<script>
    import {ListGroup, ListGroupItem} from 'sveltestrap';

    export let report = null
</script>
<div class="user-section">
    <div class="table-header">
        <span></span>
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
    .user-section {
        border-right: 1px solid #ddd;
    }

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