<script>
    import {Router, Link} from "svelte-routing";
    import NavLink from "./components/NavLink.svelte"
    import {Container, Collapse, Navbar, NavbarBrand, NavbarToggler, Nav, NavItem} from "sveltestrap";

    export let url = "";

    let isOpen = false;

    function handleUpdate(event) {
        isOpen = event.detail.isOpen;
    }
</script>

<style>
    :global(.app-header .navbar-brand) {
        font-size: 1rem;
    }
</style>

<Navbar color="light" light expand="md">
    <Container class="app-header">
        <NavbarBrand href="/" class="mr-auto">
            <span>Workload planner</span>
        </NavbarBrand>
        <NavbarToggler class="ml-2" on:click={() => (isOpen = !isOpen)}/>
        <Collapse {isOpen} navbar class="ml-2" expand="md" on:update={handleUpdate}>
            <Router {url}>
                <Nav navbar>
                    <NavItem class="d-md-none">
                        <NavLink to="/">Workload</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink to="/projects" disabled>Projects</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink to="/performance" disabled>Performance</NavLink>
                    </NavItem>
                </Nav>
            </Router>
        </Collapse>
    </Container>
</Navbar>
