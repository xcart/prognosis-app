<script>
    import {Button, Form, FormGroup, FormText, Input, Label, Row, Col} from 'sveltestrap'
    import {createEventDispatcher} from 'svelte'

    const dispatch = createEventDispatcher()

    export let query = null

    let queryInput = query
    let onFormSubmit = function (event) {
        event.preventDefault()
        dispatch('search', queryInput)
        return false;
    }
</script>
<div class="query-panel">
    <Form on:submit="{onFormSubmit}">
        <Row>
            <Col>
                <Input type="search"
                       name="query"
                       id="input-query"
                       placeholder="Input YouTrack query here"
                       readonly={false}
                       bind:value={queryInput}/>
            </Col>
            <Col class="col-auto">
                <Input type="submit" color="primary" value="Search"/>
            </Col>
        </Row>
    </Form>
</div>

<style>
    .query-panel {
        margin-bottom: 2rem;
    }
</style>