<!--suppress ES6CheckImport -->
<script>
    import { Link } from "svelte-routing";
    export let teams = null
    export let query = null

    function buildUserUrl(user) {
        return "/tasks/" + user.login
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
                    <div class="user-column">
                        <Link to="{buildUserUrl(userInfo.user)}">{userInfo.user.login}</Link>
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

    .table-body .table-row + .table-row {
        margin-top: var(--table-line-margin);
    }

    .user-column, .team-column {
        min-width: 150px;
        padding: 0 1rem;
        text-align: right;
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
    }

    .team-row {
        background: var(--table-team-bg);
    }

    .user-section .table-row {
        justify-content: flex-end;
    }

    .user-section + :global(div) {
        margin-left: 1rem;
    }
</style>