<script>
  import TaskList from "../components/usertasks/TaskList.svelte"
  import {state, storedQuery} from '../stores'
  import {onMount} from 'svelte';
  import {loadUsertasksReport} from "../actions";
  import SwimlanesCalendar from "../components/table/SwimlanesCalendar.svelte"
  import WorkloadSwimlane from "../components/table/WorkloadSwimlane.svelte"
  import SwimlaneNote from "../components/table/SwimlaneNote.svelte"

  export let login = null
  let tasks = []
  let duration = null
  let user = null
  let query = null
  let showTestingPhase = localStorage.getItem("usertasks.showTestingPhase") !== null
      ? JSON.parse(localStorage.getItem("usertasks.showTestingPhase"))
      : true

  $: if (showTestingPhase !== null) {
    localStorage.setItem("usertasks.showTestingPhase", JSON.stringify(showTestingPhase))
  }

  const MS_PER_DAY = 1000 * 60 * 60 * 24;

  function isReady(state) {
    return state.report.type === 'Usertasks' && state.report.login === login
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
      user = $state.report.user
      duration = $state.report.duration
    } else {
      query = $storedQuery
    }
  }

  onMount(() => {
    if (!isReady($state)) {
      loadUsertasksReport(login, $storedQuery)
    } else {
      storedQuery.set($state.query)
    }
  })
</script>

<section class="page">
    <div class="table-header container">
        <div></div>
        <form class="ml-auto title-aside">
            <div class="form-check">
                <input class="form-check-input" type="checkbox" bind:checked="{showTestingPhase}" value="" id="showTestingPhase">
                <label class="form-check-label" for="showTestingPhase">
                    Show testing phase
                </label>
            </div>
        </form>
    </div>
    <div class="usertasks-table">
        {#if tasks.length > 0}
            <TaskList {tasks} {user}/>
            <SwimlanesCalendar {duration}>
                {#each tasks as task}
                    <WorkloadSwimlane swimlane={task.swimlane} isSingleIssue={true} showTestingPhase={showTestingPhase}>
                        {#if task.overdue}
                            <SwimlaneNote reason="Over due date by {daysAfter(task.endDate)} days ({task.endDate})"
                                          type="danger"/>
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
    .usertasks-table {
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
