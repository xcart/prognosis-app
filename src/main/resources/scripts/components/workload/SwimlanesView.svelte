<script>
    import Dates from "../table/Dates.svelte"
    import WorkloadSwimlane from "../table/WorkloadSwimlane.svelte"
    import TeamInfo from "./parts/TeamInfo.svelte"

    export let teams = null
    export let duration = null
</script>

<div class="swimlanes-section">
    <div class="table-header">
        <Dates duration={duration}/>
    </div>
    <div class="table-body">
      {#each teams as team}
          <TeamInfo/>
          {#each team.users as user}
              <WorkloadSwimlane swimlane={user.swimlane} />
          {/each}
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

    .swimlanes-section {
        max-width: 90vw;
        overflow: scroll;
        overflow-y: hidden;
    }
</style>