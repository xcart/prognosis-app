<script>
  import Dates from "../table/Dates.svelte"
  import WorkloadSwimlane from "../table/WorkloadSwimlane.svelte"
  import TeamInfo from "./parts/TeamInfo.svelte"

  export let teams = null
  export let duration = null

  let stripOffset = () => (7 - (new Date()).getUTCDay() + 1)
</script>

<div class="swimlanes-section">
    <div class="table-header">
        <Dates duration={duration}/>
    </div>
    <div class="table-body calendar-strip" style="--data-offset: {stripOffset()}">
        {#each teams as team}
            <TeamInfo/>
            {#each team.users as user}
                <WorkloadSwimlane swimlane={user.swimlane}/>
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

    .calendar-strip {
        --data-offset: 0;
        background-image: repeating-linear-gradient(90deg,
                var(--strip-line-color) calc(var(--data-offset) * var(--table-row-width)), var(--strip-line-color) calc(var(--data-offset) * var(--table-row-width)),
                var(--strip-bg-color) calc(var(--data-offset) * var(--table-row-width) + 1px), var(--strip-bg-color) calc(var(--data-offset) * var(--table-row-width) + var(--table-row-width) * 7)
        );
    }
</style>