<script>
    import DateTableHeader from "./table/DateTableHeader.svelte"
    import Swimlane from "./table/Swimlane.svelte"

    export let report = null
    let calendarLength = Array.from(report.values()).reduce((sum, item) => {
        let length = item.items.length
        return length > sum ? length : sum
    }, 0)
</script>

<div class="swimlanes-section">
    <div class="table-header">
        <DateTableHeader duration={calendarLength}/>
    </div>
    <div class="table-body">
      {#each report as reportLine}
          <Swimlane data="{reportLine}" limit={calendarLength}/>
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