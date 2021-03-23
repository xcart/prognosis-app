<script>
  import WarningIcon from "bootstrap-icons/icons/exclamation-octagon-fill.svg"
  import DangerIcon from "bootstrap-icons/icons/x-octagon-fill.svg"

  export let reason = null
  export let type = "warning"
</script>

<div class="swimlane-note">
    <div class="reason-icon {type}">
        {#if type === "danger"}
            <DangerIcon width="20" height="20"/>
        {:else}
            <WarningIcon width="20" height="20"/>
        {/if}
    </div>
    <div class="reason-text">
        {reason}
    </div>
</div>

<style>
    .swimlane-note {
        display: flex;
        height: 100%;
        align-items: center;
    }

    .reason-icon, .reason-text {
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
        margin-left: 1rem;
    }

    .reason-text {
        margin-left: .75rem;
    }

    .reason-icon.warning {
        color: var(--icon-warning-color);
    }

    .reason-icon.danger {
        color: var(--icon-danger-color);
    }
</style>