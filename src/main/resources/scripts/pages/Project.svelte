<script>
  import {state, storedQuery} from '../stores'
  import {onMount} from 'svelte';
  import {loadProjectReport} from "../actions";
  import CalendarView from "../components/table/CalendarView.svelte"
  import NoteBlock from "../components/block/NoteBlock.svelte"
  import ProjectTaskList from "../components/project/ProjectTaskList.svelte"
  import SingleIssueBlock from "../components/block/SingleIssueBlock.svelte"
  import RowContainer from "../components/table/RowContainer.svelte"

  export let client = null
  let tasks = [],
    duration = null,
    query = null;

  let showTestingPhase = localStorage.getItem("project.showTestingPhase") !== null
    ? JSON.parse(localStorage.getItem("project.showTestingPhase"))
    : true

  $: if (showTestingPhase !== null) {
    localStorage.setItem("project.showTestingPhase", JSON.stringify(showTestingPhase))
  }

  const MS_PER_DAY = 1000 * 60 * 60 * 24;

  function isReady(state) {
    return state.report.type === 'Project' && state.report.client === client
  }

  function daysAfter(date) {
    let now = new Date()
    let due = new Date(date)
    let start = Date.UTC(due.getFullYear(), due.getMonth(), due.getDate());
    let end = Date.UTC(now.getFullYear(), now.getMonth(), now.getDate());
    return Math.floor((end - start) / MS_PER_DAY);
  }

  $: {
    if (isReady($state)) {
      // query = $state.query
      tasks = $state.report.tasks
      duration = $state.report.duration
    } else {
      // query = $storedQuery
    }
  }

  onMount(() => {
    if (!isReady($state)) {
      loadProjectReport(client)
      // loadProjectReport(client, $storedQuery)
    } else {
      // storedQuery.set($state.query)
    }
  })
</script>

<section class="page">
    <div class="table-header container">
        <div></div>
        <form class="ml-auto title-aside">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" bind:checked="{showTestingPhase}" value=""
                       id="showTestingPhase">
                <label class="form-check-label" for="showTestingPhase">
                    Show testing phase
                </label>
            </div>
        </form>
    </div>
    <div class="project-table">
        {#if tasks.length > 0}
            <ProjectTaskList {tasks}/>
            <CalendarView {duration}>
                {#each tasks as task}
                    <SingleIssueBlock swimlane={task.swimlane} showTestingPhase={showTestingPhase}>
                        {#if task.overdue}
                            <NoteBlock reason="Over due date by {daysAfter(task.endDate)} days ({task.endDate})"
                                       type="danger"/>
                        {:else if task.missedVerification}
                            <NoteBlock
                                    reason="Missed verification date by {daysAfter(task.verificationDate)} days"/>
                        {/if}
                    </SingleIssueBlock>
                {/each}
            </CalendarView>
        {:else if isReady($state)}
            <div class="container">
                <p class="lead">Alas, the list is empty :(</p>
            </div>
        {/if}
    </div>
</section>

<style>
    .project-table {
        display: flex;
    }

    .table-header {
        display: flex;
        position: relative
    }

    .title-aside {
        position: absolute;
        top: -60px;
        right: 15px;
    }
</style>
