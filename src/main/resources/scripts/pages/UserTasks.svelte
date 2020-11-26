<script>
  import TaskList from "../components/usertasks/TaskList.svelte"
  import {Container} from "sveltestrap"
  import {state, storedQuery} from '../stores'
  import {onMount} from 'svelte';
  import {loadUsertasksReport} from "../actions";
  import SwimlanesCalendar from "../components/table/SwimlanesCalendar.svelte"
  import WorkloadSwimlane from "../components/table/WorkloadSwimlane.svelte"

  export let login = null
  let tasks = [],
    duration = null,
    user = null,
    query = null;

  function isReady(state) {
    return state.report.type === 'Usertasks' && state.report.login === login
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
    <div class="usertasks-table">
        {#if tasks.length > 0}
            <TaskList {tasks} {user}/>
            <SwimlanesCalendar {duration}>
                {#each tasks as task}
                    <WorkloadSwimlane swimlane={task.swimlane} isSingleIssue={true}/>
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
</style>
