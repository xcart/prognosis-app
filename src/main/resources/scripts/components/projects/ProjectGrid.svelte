<script>
  import {tooltip} from "../../actions/tooltip";
  import {Container, Collapse, Navbar, NavbarBrand, NavbarToggler, Nav, NavItem} from "sveltestrap";
  import ProjectCard from "./parts/ProjectCard.svelte"

  export let groups = null
</script>

<div class="project-section">
    <div class="table-header">
        <Navbar light expand="xs">
            <span class="navbar-text ml-auto mr-2">
              Display:
            </span>
            <Nav navbar>
                <NavItem active>
                    <span class="nav-link">Cards</span>
                </NavItem>
                <NavItem>
                    <span class="nav-link">Timeline</span>
                </NavItem>
            </Nav>
        </Navbar>
    </div>
    <div class="table-body">
        {#each groups as group}
            <div class="group-heading">
                <h3>
                    <span>{group.manager.login}</span>
                    <span class="projects-count badge badge-light">{group.projects.length} projects</span>
                </h3>
            </div>
            <div class="group-projects">
                {#each group.projects as project}
                    <ProjectCard project={project}></ProjectCard>
                {/each}
            </div>
        {/each}
    </div>
</div>

<style>
    .project-section {
        width: 100%;
    }
    .table-header {
        margin-bottom: var(--table-line-margin);
    }

    .group-projects {
        display: grid;
        grid-template-columns: repeat(3, 1fr);
        grid-gap: var(--grid-gap);
        margin-right: -1.25rem;
        margin-left: -1.25rem;
    }

    @media (max-width: 991px) {
        .group-projects {
            grid-template-columns: repeat(2, 1fr);
        }
    }

    @media (max-width: 767px) {
        .group-projects {
            grid-template-columns: repeat(1, 1fr);
        }
    }

    .group-heading {
        margin-top: var(--grid-group-margin);
        margin-bottom: calc(var(--grid-group-margin) / 2);
    }

    .group-heading .projects-count {
        margin-left: 1rem;
    }
</style>