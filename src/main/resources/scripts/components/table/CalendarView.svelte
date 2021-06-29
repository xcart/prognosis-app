<script>
  import Dates from "../table/Dates.svelte"
  export let duration = null
  $: {
    if (duration < 40) {
      duration = 40
    }
  }

  let stripOffset = () => (7 - (new Date()).getUTCDay() + 1)
</script>

<div class="swimlanes-section">
    <div class="table-header">
        <Dates duration={duration}/>
    </div>
    <div class="table-body calendar-strip" style="--data-offset: {stripOffset()}">
        <slot></slot>
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

    :global(.table-body .table-row + .table-row) {
        margin-top: var(--table-line-margin);
    }

    .swimlanes-section {
        max-width: 90vw;
        overflow: scroll;
        overflow-y: hidden;
    }

    .calendar-strip {
        width: 100%;
        --data-offset: 0;
        background-image: repeating-linear-gradient(90deg,
                var(--strip-line-color) calc(var(--data-offset) * var(--table-cell-width)), var(--strip-line-color) calc(var(--data-offset) * var(--table-cell-width)),
                var(--strip-bg-color) calc(var(--data-offset) * var(--table-cell-width) + 1px), var(--strip-bg-color) calc(var(--data-offset) * var(--table-cell-width) + var(--table-cell-width) * 7)
        );
    }
</style>