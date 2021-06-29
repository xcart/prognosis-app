<script>
    export let amount = 0
</script>

{#if amount > 0}
<div class="offset-block"
     style="--data-size: {amount}"></div>
{/if}

<style>
    .offset-block {
        width: calc(var(--table-cell-width) * var(--data-size));
        min-width: calc(var(--table-cell-width) * var(--data-size));
        max-width: calc(var(--table-cell-width) * var(--data-size));
        display: inline-block;
        height: 100%;
        position: relative;
        text-align: center;
        background: #ddddddbb;
        visibility: hidden;
    }
</style>