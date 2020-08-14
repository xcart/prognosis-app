<script>
    import UserList from "../components/workload/UserList.svelte"
    import SwimlanesView from "../components/workload/SwimlanesView.svelte"
    import QueryPanel from "../components/workload/QueryPanel.svelte"
    import {Container} from "sveltestrap"
    import {state} from '../stores'
    import {performSearch} from "../actions"

    let users,
        teams,
        duration,
        query;

    $: query = $state.query
    $: users = $state.report.teams.reduce((list, team) => {
        return list.concat(team.users.map((workload) => {
            return workload.user
        }))
    }, [])
    $: teams = $state.report.teams
    $: duration = $state.report.duration
</script>

<section class="page">
    <Container>
        <QueryPanel query={query} on:search="{(event) => performSearch(event.detail)}"></QueryPanel>
    </Container>
    <div class="workload-table">
        <UserList teams={teams}/>
        <SwimlanesView teams={teams} duration={duration}/>
    </div>
</section>

<style>
    :root {
        --table-row-height: 2rem;
        --table-row-width: 2rem;
        --table-line-margin: .5rem;
        --table-border: 1px solid #eee;
        --table-team-bg: #eee;
    }

    .workload-table {
        display: flex;
    }
</style>
