<!--suppress ES6CheckImport -->
<script>
    import { Link } from "svelte-routing";
    import {storedQuery} from "../../stores"

    export let teams = null

    function buildUserUrl(user) {
        return "/tasks/" + user.login + "?query=" + encodeURIComponent($storedQuery)
    }
</script>

<div class="user-section">
    <div class="table-header">
        <div class="table-row">
            <span></span>
        </div>
        <div class="table-row">
            <span></span>
        </div>
    </div>
    <div class="table-body">
        {#each teams as team}
            <div class="table-row team-row">
                <div class="team-column">
                    {#if team.teamName == 'NoTeam'}
                        <span>Without team</span>
                    {:else}
                        <span>Team {team.teamName}</span>
                    {/if}
                </div>
            </div>
            {#each team.users as userInfo}
                <div class="table-row">
                    <div class="avatar-column">
                        <img src="{userInfo.user.avatarUrl}" alt="{userInfo.user.login} avatar"/>
                    </div>
                    <div class="user-column">
                        <small class="user-login"><Link to="{buildUserUrl(userInfo.user)}">{userInfo.user.login}</Link></small>
                        <small class="user-summary">16h</small>
                    </div>
                </div>
            {/each}
        {/each}
    </div>
</div>

<style>
    .user-section {
        border-right: var(--table-border);
    }

    .table-header {
        margin-bottom: var(--table-line-margin);
    }

    .table-row {
        display: flex;
        height: var(--table-row-height);
        min-height: var(--table-row-height);
        align-items: center;
    }

    .table-body .table-row {
        height: var(--table-extended-row-height);
    }

    .table-body .table-row + .table-row {
        margin-top: var(--table-line-margin);
    }

    .user-column, .team-column {
        min-width: 118px;
        padding: 0 1rem;
    }

    .avatar-column, .user-column, .team-column {
        text-align: right;
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
    }

    .avatar-column {
        width: 32px;
    }

    .avatar-column img {
        border-radius: 3px;
        max-width: 100%;
        max-height: 100%;
    }

    .team-row {
        background: var(--table-team-bg);
    }

    .user-summary {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }

    .user-section .table-row {
        justify-content: flex-end;
    }
</style>