<script>
    export let className = ""
</script>

<div class="row-container {className}">
    <slot></slot>
</div>

<style>
    .row-container {
        display: flex;
        height: var(--table-extended-row-height);
        padding: var(--table-extended-row-v-padding);
        align-items: center;
    }

    :global(.row-container) + :global(.row-container) {
        margin-top: var(--table-line-margin);
    }
</style>