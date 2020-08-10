<script lang="ts">
    import UserList from "../components/workload/UserList.svelte"
    import SwimlanesView from "../components/workload/SwimlanesView.svelte"
    import type {User, TeamWorkload} from "../types"
    import {state} from '../stores'

    let users: Array<User>,
        teams: Array<TeamWorkload>,
        duration: Number;

    $: {

    }
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
        <UserList users={users}/>
        <SwimlanesView teams={teams} duration={duration}/>
    </div>
</section>

<style>
    :root {
        --table-row-height: 2rem;
        --table-row-width: 2rem;
        --table-line-margin: .5rem;
        --table-border: 1px solid #ddd;
    }

    .workload-table {
        display: flex;
    }
</style>
