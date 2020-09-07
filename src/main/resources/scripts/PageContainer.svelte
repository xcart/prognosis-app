<script>
  import {Router, Route} from "svelte-routing";
  import {Container} from "sveltestrap"
  import PageHeader from "./components/PageHeader.svelte";
  import Workload from "./pages/Workload.svelte";
  import UserTasks from "./pages/UserTasks.svelte";
  import Projects from "./pages/Projects.svelte"

  export let url = "";
</script>

<Router {url}>
    <Route path="/">
        <Container class="page-container" fluid>
            <Workload/>
        </Container>
    </Route>
    <Route path="/projects">
        <Container class="page-container" fluid>
            <Projects/>
        </Container>
    </Route>
    <Route path="/tasks/:login" let:params>
        <PageHeader>
            {params.login} tasks
        </PageHeader>
        <Container class="page-container" fluid>
            <UserTasks user="{params.login}"/>
        </Container>
    </Route>
</Router>

<style>
    :global(.page-container) {
        margin-top: 2rem;
        margin-bottom: 2rem;
    }
</style>