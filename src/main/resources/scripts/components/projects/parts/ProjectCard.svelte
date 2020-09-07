<script>
  import {Card, CardTitle, CardText, CardLink} from "sveltestrap"

  export let project = null;
  let formatEstimation = (value) => (value / 60.0).toFixed(1) + "h"
</script>

<Card body class="project-card">
    <CardTitle>
        <a class="project-link" href="https://xcart.myjetbrains.com/youtrack/issue/"
           target="_blank">
            {project.client}
        </a>
    </CardTitle>
    <CardText>
        <span class="estimation-sum badge badge-info">{formatEstimation(project.estimation)}</span>
        {#if project.endDate}
            <span class="end-date badge badge-light">till {project.endDate}</span>
        {/if}
    </CardText>
    <CardText>
        <span class="project-team">Team: </span>
        {#each project.team as member}
            <span class="team-member badge badge-light">{member.login}</span>
        {/each}
    </CardText>
    <CardLink href="#">{project.tasks.length} active tasks</CardLink>
</Card>

<style>
    :global(.project-card .card-text) {
        margin-bottom: .5rem;
    }

    :global(.project-card .card-title) {
        overflow: hidden;
    }

    .project-link {
        text-overflow: ellipsis;
        overflow: hidden;
        white-space: nowrap;
    }
</style>