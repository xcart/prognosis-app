<script>
  export let report = null
</script>
<div class="user-section">
    <div class="table-header">
        <span>-------</span>
    </div>
    <div class="table-body">
        {#each report as reportLine}
        <div class="table-row">
            <div class="user-column">
                <span>{reportLine.user.fullName}</span>
            </div>
        </div>
        {/each}
    </div>
</div>

<style>
.table-header {
  display: flex;
}

.table-row {
  display: flex;
  height: 2rem;
  min-height: 2rem;
  align-items: center;
}

.table-header + .table-body {
  margin-top: 1rem;
}

.user-section .table-row {
  justify-content: flex-end;
}

.user-section + :global(div) {
  margin-left: 1rem;
}
</style>