<script>
  import ProjectCard from "./cards/ProjectCard.svelte"

  export let groups = []
  let formatEstimation = (value) => (value / 60.0).toFixed(1)
</script>

<div class="table-body project-cards">
    {#each groups as group}
        <div class="group-heading">
            <h3>
                <span>{group.manager.login}</span>
                <span class="projects-count badge badge-light">{group.projects.length} projects</span>
                <span class="estimation-total badge badge-light">{formatEstimation(group.estimation)}h total</span>
            </h3>
        </div>
        <div class="group-projects">
            {#each group.projects as project}
                <ProjectCard project={project}></ProjectCard>
            {/each}
        </div>
    {/each}
</div>

<style>
    .group-projects {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        grid-gap: var(--grid-gap);
        margin-right: -1.25rem;
        margin-left: -1.25rem;
    }

    @media (max-width: 991px) {
        .group-projects {
            grid-template-columns: repeat(2, 1fr);
        }
    }

    @media (max-width: 767px) {
        .group-projects {
            grid-template-columns: repeat(1, 1fr);
        }
    }

    .group-heading {
        margin-top: var(--grid-group-margin);
        margin-bottom: calc(var(--grid-group-margin) / 2);
    }

    .group-heading .projects-count {
        margin-left: 1rem;
    }
</style>