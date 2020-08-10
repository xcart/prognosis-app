<!--suppress ES6CheckImport -->
<script lang="ts">
    import type {User} from "../../types"
    export let users: Array<User> = null
</script>

<div class="user-section">
    <div class="table-header">
        <div class="table-row">
            <span></span>
        </div>
        <div class="table-row">
            <span></span>
        </div>
    </div>
    <div class="table-body">
      {#each users as user}
          <div class="table-row">
              <div class="user-column">
                  <span>{user.login}</span>
              </div>
          </div>
      {/each}
    </div>
</div>

<style>
    .user-section {
        border-right: var(--table-border);
    }

    .table-header {
        margin-bottom: var(--table-line-margin);
    }

    .table-row {
        display: flex;
        height: var(--table-row-height);
        min-height: var(--table-row-height);
        align-items: center;
    }

    .table-body .table-row + .table-row {
        margin-top: var(--table-line-margin);
    }

    .user-column {
        min-width: 120px;
        padding: 0 1rem;
        text-align: right;
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
    }

    .user-section .table-row {
        justify-content: flex-end;
    }

    .user-section + :global(div) {
        margin-left: 1rem;
    }
</style>