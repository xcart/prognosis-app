<script>
    import {tooltip} from "../../actions/tooltip";
    import SummaryTooltip from "./parts/SummaryTooltip.svelte"

    export let tasks = null
</script>

<div class="task-section">
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
                <div class="task-column">
                    <a class="issue-link" href="https://xcart.myjetbrains.com/youtrack/issue/{task.issue.idReadable}"
                       target="_blank"><small>{task.issue.idReadable}</small></a>
                    <small class="issue-summary"
                        use:tooltip={{component: SummaryTooltip, props: {summary: task.issue.summary}, interactive: false, display: true }}
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

    .table-body .table-row + .table-row {
        margin-top: var(--table-line-margin);
    }

    .task-column {
        max-width: 150px;
        min-width: 150px;
        padding: 0 1rem;
        text-align: right;
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
    }

    .task-section .table-row {
        justify-content: flex-end;
    }

    .task-section + :global(div) {
        margin-left: 1rem;
    }
</style>