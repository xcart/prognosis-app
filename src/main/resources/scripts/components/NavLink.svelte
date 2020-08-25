<script>
  import { Link } from "svelte-routing";

  export let to = "";
  export let disabled = false

  function getProps({ location, href, isPartiallyCurrent, isCurrent }) {
    const isActive = href === "/" ? isCurrent : isPartiallyCurrent || isCurrent;

    if (disabled) {
      return { class: "nav-link disabled" }
    }

    // The object returned here is spread on the anchor element's attributes
    if (isActive) {
      return { class: "nav-link active" };
    }
    return { class: "nav-link" };
  }
</script>

<Link {to} {getProps}>
  <slot />
</Link>