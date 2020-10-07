<script>
    export let groups = null

  let formatEstimation = (value) => (value / 60.0).toFixed(1) + "h"
</script>

<div class="manager-list-section">
    <div class="table-header">
        <div class="table-row">
            <span></span>
        </div>
        <div class="table-row">
            <span></span>
        </div>
    </div>
    <div class="table-body">
        {#each groups as group}
            <div class="table-row">
                <div class="user-column">
                    <span>{group.manager.login}</span>
<!--                    <a class="issue-link" href="https://xcart.myjetbrains.com/youtrack/issue/{task.issue.idReadable}"-->
<!--                       target="_blank"><small>{task.issue.idReadable}</small></a>-->
                    <small>{group.projects.length} projects</small>
                    <small>{formatEstimation(group.estimation)}</small>
                </div>
            </div>
            {#each group.projects as project}
                <div class="table-row fake"></div>
            {/each}
        {/each}
    </div>
</div>

<style>
    .manager-list-section {
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

    .user-column {
        max-width: 150px;
        min-width: 150px;
        padding: 0 1rem;
        text-align: right;
        display: flex;
        height: 100%;
        justify-content: flex-start;
        flex-direction: column;
        position: relative;
    }

    .manager-list-section .table-row {
        justify-content: flex-end;
    }

    .manager-list-section + :global(div) {
        margin-left: 1rem;
    }
</style>