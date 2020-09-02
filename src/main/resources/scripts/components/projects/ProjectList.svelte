<script>
    import {tooltip} from "../../actions/tooltip";
    import ProjectNameTooltip from "./parts/ProjectNameTooltip.svelte"

    export let projects = null
</script>

<div class="project-section">
    <div class="table-header">
    </div>
    <div class="table-body">
        {#each projects as project}
            <div class="table-row">
                <div class="project-column">
                    <a class="project-link" href="https://xcart.myjetbrains.com/youtrack/issue/"
                       target="_blank">
                        <span use:tooltip={{component: ProjectNameTooltip, props: {name: project.client}, interactive: false, display: true }}>
                            {project.client}
                        </span>
                    </a>
                    <span class="project-manager">PM: {project.manager.login}</span>
                </div>
            </div>
        {/each}
    </div>
</div>

<style>
    .project-link {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }

    .project-section {
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
        height: var(--table-large-row-height);
    }

    .project-link, .project-manager {
        /*line-height: 1rem;*/
    }

    .table-body .table-row + .table-row {
        margin-top: var(--table-line-margin);
    }

    .project-column {
        max-width: 200px;
        min-width: 200px;
        padding: 0 1rem;
        text-align: right;
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
    }

    .project-section .table-row {
        justify-content: flex-end;
    }

    .project-section + :global(div) {
        margin-left: 1rem;
    }
</style>