<script>
    import {Router, Route} from "svelte-routing";
    import {Container} from "sveltestrap"
    import PageHeader from "./components/PageHeader.svelte";
    import Workload from "./pages/Workload.svelte";
    import About from "./pages/About.svelte";

    export let url = "";
</script>

<Router {url}>
    <Route path="/">
        <PageHeader>
            Workload analysis
        </PageHeader>
        <Container class="page-container" fluid>
            <Workload/>
        </Container>
    </Route>
    <Route path="/about">
        <PageHeader>
            About page
        </PageHeader>
        <Container class="page-container" fluid>
            <About/>
        </Container>
    </Route>
</Router>

<style>
    :global(.page-container) {
        margin-top: 2rem;
        margin-bottom: 2rem;
    }
</style>