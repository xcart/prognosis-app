<script lang="ts">
    import Dates from "./table/Dates.svelte"
    import Swimlane from "./table/Swimlane.svelte"
    import type {SwimlaneRow} from "../../types"
    import LabelRow from "./table/LabelRow.svelte"

    export let rows: Array<SwimlaneRow> = null
    export let duration: Number = null
</script>

<div class="swimlanes-section">
    <div class="table-header">
        <Dates duration={duration}/>
    </div>
    <div class="table-body">
      {#each rows as row}
        {#if row.swimlane}
            <Swimlane swimlane={row.swimlane} />
        {:else}
            <LabelRow label={row.label} />
        {/if}
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
    }
</style>