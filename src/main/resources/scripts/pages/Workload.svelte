<script>
    import UserList from "../components/workload/UserList.svelte"
    import SwimlanesView from "../components/workload/SwimlanesView.svelte"
    import {state} from '../stores'

    let users,
        teams,
        duration,
        rows;

    $: users = $state.report.teams.reduce((list, team) => {
        return list.concat(team.users.map((workload) => {
            return workload.user
        }))
    }, [])
    $: teams = $state.report.teams
    $: duration = $state.report.duration
</script>

<section class="page">
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
