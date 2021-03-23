<script>
  import WarningIcon from "bootstrap-icons/icons/exclamation-octagon-fill.svg"
  import DangerIcon from "bootstrap-icons/icons/x-octagon-fill.svg"

  export let reason = null
  export let type = "warning"
</script>

<div class="table-row">
    <div class="reason-icon {type}">
        {#if type === "danger"}
            <DangerIcon width="24"/>
        {:else}
            <WarningIcon width="24"/>
        {/if}
    </div>
    <div class="reason-text">
        {reason}
    </div>
</div>

<style>
    .table-row {
        display: flex;
        height: var(--table-extended-row-height);
        padding: var(--table-extended-row-v-padding);
        align-items: center;
    }

    .reason-icon, .reason-text {
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
        margin-left: .5rem;
    }

    .reason-icon.warning {
        color: var(--icon-warning-color);
    }

    .reason-icon.danger {
        color: var(--icon-danger-color);
    }
</style>