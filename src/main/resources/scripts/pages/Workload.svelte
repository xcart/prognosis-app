<script>
  import UserList from "../components/workload/UserList.svelte"
  import QueryPanel from "../components/workload/QueryPanel.svelte"
  import {Container} from "sveltestrap"
  import {state, storedQuery} from '../stores'
  import {onMount} from 'svelte';
  import {loadWorkloadReport} from "../actions"
  import SwimlanesCalendar from "../components/table/SwimlanesCalendar.svelte"
  import TeamInfo from "../components/workload/parts/TeamInfo.svelte"
  import WorkloadSwimlane from "../components/table/WorkloadSwimlane.svelte"

  let users = [],
    teams = [],
    duration = null,
    query = null;

  function isReady(state) {
    return state.report.type === 'Workload'
  }

  $: {
    if (isReady($state)) {
      query = $state.query
      users = $state.report.teams.reduce((list, team) => {
        return list.concat(team.users.map((workload) => {
          return workload.user
        }))
      }, [])
      teams = $state.report.teams
      duration = $state.report.duration
    } else {
      query = $storedQuery
    }
  }

  onMount(() => {
    if (!isReady($state)) {
      loadWorkloadReport($storedQuery)
    } else {
      storedQuery.set($state.query)
    }
  })
</script>

<section class="page">
    <Container>
        <QueryPanel query={query} on:search="{(event) => loadWorkloadReport(event.detail)}"/>
    </Container>
    <div class="workload-table">
        <UserList {teams}/>
        <SwimlanesCalendar {duration}>
            {#each teams as team}
                <TeamInfo/>
                {#each team.users as user}
                    <WorkloadSwimlane swimlane={user.swimlane}/>
                {/each}
            {/each}
        </SwimlanesCalendar>
    </div>
</section>

<style>
    .workload-table {
        display: flex;
    }
</style>
