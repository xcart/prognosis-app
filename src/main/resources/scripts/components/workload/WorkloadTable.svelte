<script>
    import DateTableHeader from "./table/DateTableHeader.svelte"
    import Swimlane from "./table/Swimlane.svelte"

    export let report = null
    let calendarLength = 30
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
    }
</style>