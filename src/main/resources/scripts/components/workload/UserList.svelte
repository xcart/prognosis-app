<!--suppress ES6CheckImport -->
<script>
  import {Link} from "svelte-routing";
  import {storedQuery} from "../../stores"
  import Avatar from "../common/Avatar.svelte"
  import {tooltip} from "../../actions/tooltip";
  import UserSummaryTooltip from "./parts/UserSummaryTooltip.svelte"

  export let teams = null

  const formatWorkload = (value) => (value / 60.0).toFixed(1) + "h"

  const getOverdueMinutes = (stats) => {
    let stat = stats.find(it => it.key === "OverdueEstimation")
    return stat ? stat.value : 0
  }

  const formatStats = (stats) => formatWorkload(getOverdueMinutes(stats))

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
                        <Avatar user={userInfo.user} />
                    </div>
                    <div class="user-column">
                        <small class="user-login"><Link to="{buildUserUrl(userInfo.user)}">{userInfo.user.login}</Link></small>
                        <small class="user-summary" use:tooltip={{component: UserSummaryTooltip, props: {stats: userInfo.stats}}}>
                            <span class="overdue-value {getOverdueMinutes(userInfo.stats) > 300 ? 'non-zero' : 'zero'}">{formatStats(userInfo.stats)}</span>
                        </small>
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
    .team-row {
        background: var(--table-team-bg);
    }

    .user-summary {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }

    .overdue-value {
        border-bottom: 1px dashed #21252980;
    }

    .overdue-value.non-zero {
        color: red;
        border-bottom: 1px dashed #d8000085;
    }

    .user-section .table-row {
        justify-content: flex-end;
    }
</style>