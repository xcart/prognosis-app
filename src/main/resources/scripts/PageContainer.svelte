<script>
  import {Router, Route} from "svelte-routing";
  import {Container} from "sveltestrap"
  import PageHeader from "./components/PageHeader.svelte";
  import Workload from "./pages/Workload.svelte";
  import UserTasks from "./pages/UserTasks.svelte";
  import Projects from "./pages/Projects.svelte"
  import Project from "./pages/Project.svelte"
  import Avatar from "./components/common/Avatar.svelte"

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
    <Route path="/projects/:client" let:params>
        <PageHeader>
            {params.client} tasks
        </PageHeader>
        <Container class="page-container" fluid>
            <Project client="{params.client}"/>
        </Container>
    </Route>
    <Route path="/tasks/:login" let:params>
        <PageHeader>
            {params.login} tasks
        </PageHeader>
        <Container class="page-container" fluid>
            <UserTasks login="{params.login}"/>
        </Container>
    </Route>
</Router>

<style>
    :global(.page-container) {
        margin-top: 2rem;
        margin-bottom: 2rem;
    }
</style>