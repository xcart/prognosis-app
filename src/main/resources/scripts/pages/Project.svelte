<script>
  import {state, storedQuery} from '../stores'
  import {onMount} from 'svelte';
  import {loadProjectReport} from "../actions";
  import SwimlanesCalendar from "../components/table/SwimlanesCalendar.svelte"
  import WorkloadSwimlane from "../components/table/WorkloadSwimlane.svelte"
  import SwimlaneNote from "../components/table/SwimlaneNote.svelte"
  import ProjectTaskList from "../components/project/ProjectTaskList.svelte"

  export let client = null
  let tasks = [],
    duration = null,
    // user = null,
    query = null;

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
      query = $state.query
      tasks = $state.report.tasks
      // user = $state.report.user
      duration = $state.report.duration
    } else {
      query = $storedQuery
    }
  }

  onMount(() => {
    if (!isReady($state)) {
      loadProjectReport(client, $storedQuery)
    } else {
      storedQuery.set($state.query)
    }
  })
</script>

<section class="page">
    <div class="project-table">
        {#if tasks.length > 0}
            <ProjectTaskList {tasks}/>
            <SwimlanesCalendar {duration}>
                {#each tasks as task}
                    <WorkloadSwimlane swimlane={task.swimlane} isSingleIssue={true}>
                        {#if task.overdue}
                            <SwimlaneNote reason="Over due date by {daysAfter(task.endDate)} days ({task.endDate})" type="danger"/>
                        {:else if task.missedVerification}
                            <SwimlaneNote reason="Missed verification date by {daysAfter(task.verificationDate)} days"/>
                        {/if}
                    </WorkloadSwimlane>
                {/each}
            </SwimlanesCalendar>
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
</style>
