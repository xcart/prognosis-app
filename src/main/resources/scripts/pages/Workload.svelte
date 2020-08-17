<script>
    import UserList from "../components/workload/UserList.svelte"
    import SwimlanesView from "../components/workload/SwimlanesView.svelte"
    import QueryPanel from "../components/workload/QueryPanel.svelte"
    import {Container} from "sveltestrap"
    import {state, storedQuery} from '../stores'
	import {onMount} from 'svelte';
    import {loadWorkloadReport} from "../actions"

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
        <QueryPanel query={query} on:search="{(event) => loadWorkloadReport(event.detail)}"></QueryPanel>
    </Container>
    <div class="workload-table">
        <UserList {teams} {query}/>
        <SwimlanesView {teams} {duration}/>
    </div>
</section>

<style>
    .workload-table {
        display: flex;
    }
</style>
