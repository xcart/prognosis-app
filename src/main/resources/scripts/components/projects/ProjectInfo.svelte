<script>
  // import Dates from "../table/Dates.svelte"
  // import TaskSwimlane from "./parts/TaskSwimlane.svelte"

  export let projects = null

  let formatEstimation = (value) => (value / 60.0).toFixed(1) + "h"
</script>

<div class="project-info-section">
    <div class="table-header">
    </div>
    <div class="table-body">
        {#each projects as project}
            <div class="table-row">
                <div class="project-stats badge-row">
                    <a href="#" class="tasks-number badge badge-primary">{project.tasks.length} active tasks</a>
                    <span class="estimation-sum badge badge-info">{formatEstimation(project.estimation)}</span>
                    {#if project.endDate}
                        <span class="end-date badge badge-light">till {project.endDate}</span>
                    {/if}
                </div>
                <div class="project-team badge-row">
                    <small>Team: </small>
                    {#each project.team as member}
                        <span class="team-member badge badge-light">{member.login}</span>
                    {/each}
                </div>
            </div>
        {/each}
    </div>
</div>

<style>
    .table-header {
        border-bottom: var(--table-border);
        margin-bottom: var(--table-line-margin);
        width: max-content;
    }

    .table-body {
        width: max-content;
    }

    .table-row {
        display: flex;
        justify-content: center;
        flex-direction: column;
        min-height: var(--table-large-row-height);
        height: var(--table-large-row-height);
    }

    .table-body .table-row + .table-row {
        margin-top: var(--table-line-margin);
    }

    .project-info-section {
        max-width: 90vw;
        overflow: scroll;
        overflow-y: hidden;
    }
</style>