<script>
  import {tooltip} from "../../actions/tooltip";
  import {Container, Collapse, Navbar, NavbarBrand, NavbarToggler, Nav, NavItem} from "sveltestrap";
  import ProjectCards from "./ProjectCards.svelte"
  import ProjectTimeline from "./ProjectTimeline.svelte"

  export let groups = null
  export let duration = null
  let displayMode = localStorage.getItem("projects.displayMode") || "timeline"

  let displayCards = () => {
    displayMode = "cards"
    localStorage.setItem("projects.displayMode", displayMode)
  }
  let displayTimeline = () => {
    displayMode = "timeline"
    localStorage.setItem("projects.displayMode", displayMode)
  }
</script>

<div class="project-section">
    <div class="table-header">
        <Container>
            <Navbar light expand="xs">
                <span class="navbar-text mr-2">
                  Sorted by:
                </span>
                <span class="navbar-text">
                  Estimation ⤵️
                </span>
            </Navbar>
            <Navbar light expand="xs" class="ml-auto">
                <span class="navbar-text mr-2">
                  Display:
                </span>
                <Nav navbar>
                    <NavItem active={displayMode === "cards"}>
                        <span class="nav-link" on:click={displayCards}>Cards</span>
                    </NavItem>
                    <NavItem active={displayMode === "timeline"}>
                        <span class="nav-link" on:click={displayTimeline}>Timeline</span>
                    </NavItem>
                </Nav>
            </Navbar>
        </Container>
    </div>
    {#if displayMode == "cards"}
        <Container>
            <ProjectCards groups={groups}></ProjectCards>
        </Container>
    {:else}
        <Container fluid>
            <ProjectTimeline groups={groups} duration={duration}></ProjectTimeline>
        </Container>
    {/if}
</div>

<style>
    .project-section {
        width: 100%;
    }

    .table-header {
        margin-bottom: var(--table-line-margin);
    }

    :global(.table-header .container) {
        display: flex;
        padding: 0;
    }

    .nav-link {
        cursor: pointer;
    }
</style>