<script>
  import {tooltip} from "../../actions/tooltip";
  import SummaryTooltip from "./parts/SummaryTooltip.svelte"
  import IssueState from "../common/IssueState.svelte"
  import Avatar from "../common/Avatar.svelte"

  export let tasks = null
  export let user = null
</script>

<div class="task-section">
    <div class="table-header">
        <div class="table-row">
            <Avatar {user} size="large" />
        </div>
        <div class="table-row">
            <span></span>
        </div>
    </div>
    <div class="table-body">
        {#each tasks as task}
            <div class="table-row">
                <div class="issue-state-column">
                    <IssueState state={task.issue.state} />
                </div>
                <div class="task-column">
                    <a class="issue-link" href="https://xcart.myjetbrains.com/youtrack/issue/{task.issue.idReadable}"
                       target="_blank">
                        <small>{task.issue.idReadable}</small>
                    </a>
                    <small class="issue-summary"
                           use:tooltip={{component: SummaryTooltip, props: {summary: task.issue.summary}, interactive: false}}
                    >{task.issue.summary}</small>
                </div>
            </div>
        {/each}
    </div>
</div>

<style>
    .issue-summary {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }

    .task-section {
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

    .issue-link, .issue-summary {
        line-height: 1rem;
    }

    .task-column {
        max-width: 176px;
        min-width: 176px;
        padding: 0 1rem;
        text-align: right;
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
    }

    .task-section :global(.user-avatar-block) {
        align-self: flex-start;
    }
</style>