<script>
    import {Container} from "sveltestrap";
</script>

<Container class="page-header">
    <h1>
        <slot></slot>
    </h1>
</Container>

<style>
    :global(.page-header) {
        margin-top: 2rem;
    }
</style>