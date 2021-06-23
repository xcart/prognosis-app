import { differenceInCalendarDays } from 'date-fns'

export function applyMoveChanges(original, changes) {
  let result = JSON.parse(JSON.stringify(original))
  for (const id in changes) {
    let item = result.find(i => i.issue.id === id)
    if (item) {
      item.offset = item.offset + changes[id].shiftAmount
      item.swimlane = changes[id].data.swimlane
    }
  }
  return result
}