<script>
    import {Router, Link} from "svelte-routing";
    import NavLink from "./components/NavLink.svelte"
    import {Container, Collapse, Navbar, NavbarToggler, Nav, NavItem} from "sveltestrap";

    export let url = "";

    let isOpen = false;

    function handleUpdate(event) {
        isOpen = event.detail.isOpen;
    }
</script>

<Navbar color="light" light expand="md">
    <Container class="app-header">
        <NavbarToggler on:click={() => (isOpen = !isOpen)}/>
        <Collapse {isOpen} navbar expand="md" on:update={handleUpdate}>
            <Router {url}>
                <Nav navbar>
                    <NavItem>
                        <NavLink to="/">Workload</NavLink>
                    </NavItem>
                    <NavItem>
                        <NavLink to="/about">About</NavLink>
                    </NavItem>
                </Nav>
            </Router>
        </Collapse>
    </Container>
</Navbar>
