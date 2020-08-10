<script lang="ts">
    export let label: String = null
</script>

<div class="table-row label-row">
    <div class="full-width-column">
        <span>{label}</span>
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

    .full-width-column {
        width: 100%;
        display: flex;
        height: 100%;
        justify-content: center;
        flex-direction: column;
        position: relative;
        text-align: center;
    }
</style>