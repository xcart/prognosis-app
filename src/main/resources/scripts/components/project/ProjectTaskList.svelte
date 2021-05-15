<script>
  import {tooltip} from "../../actions/tooltip";
  import TaskSummaryTooltip from "../common/TaskSummaryTooltip.svelte"
  import IssueState from "../common/IssueState.svelte"
  import Avatar from "../common/Avatar.svelte"

  export let tasks = null

  const clientRegex = /^\[.+?\]\s+(.*)/

  let formatTaskName = (name) => {
    let matches = name.match(clientRegex)
    return matches
      ? matches[1]
      : name;
  }
</script>

<div class="project-task-section">
    <div class="table-header">
        <div class="table-row">
            <span></span>
        </div>
        <div class="table-row">
            <span></span>
        </div>
    </div>
    <div class="table-body">
        {#each tasks as task}
            <div class="table-row">
                <div class="avatar-column">
                    <Avatar user={task.assignee}/>
                </div>
                <div class="issue-state-column">
                    <IssueState state={task.issue.state}/>
                </div>
                <div class="task-column">
                    <a class="issue-link" href="https://xcart.myjetbrains.com/youtrack/issue/{task.issue.idReadable}"
                       target="_blank">
                        <small>{task.issue.idReadable}</small>
                    </a>
                    <small class="issue-summary"
                           use:tooltip={{component: TaskSummaryTooltip, props: {summary: task.issue.summary}, interactive: false}}
                    >{formatTaskName(task.issue.summary)}</small>
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

    .project-task-section {
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

    .issue-state-column {
        padding-left: 1rem;
    }

    .project-task-section :global(.user-avatar-block) {
        align-self: flex-start;
    }
</style>