<script lang="ts">
    export let team: String = null
</script>

<div class="table-row team-swimlane">
  <div class="full-column">
      <span></span>
  </div>
</div>

<style>
    .table-row {
        display: flex;
        height: 2rem;
        min-height: 2rem;
        align-items: center;
    }

    .table-row + .table-row {
        margin-top: var(--table-line-margin);
    }

    .full-column {
        width: 100%;
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
        text-align: left;
    }
</style>