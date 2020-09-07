<script>
  import {Card, CardTitle, CardText, CardLink} from "sveltestrap"
  import {storedQuery} from '../../../stores'

  export let project = null;
  let formatEstimation = (value) => (value / 60.0).toFixed(1) + "h"
  let getClientLink = (client) => {
    return "https://xcart.myjetbrains.com/youtrack/issues?q="
      + encodeURIComponent($storedQuery)
      + encodeURIComponent(" Client: {" + client + "}")
  }
</script>

<Card body class="project-card">
    <CardTitle>
        <a class="project-link" href={getClientLink(project.client)}
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
    <a class="card-link" target="_blank" href={getClientLink(project.client)}>{project.tasks.length} active tasks</a>
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