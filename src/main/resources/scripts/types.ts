export type WorkloadReport = {
    teams: Array<TeamWorkload>
    duration: Number
}

export type TeamWorkload = {
    teamName: String
    users: Array<UserWorkload>
    stats: Array<StatValue>
}

export type UserWorkload = {
    user: User
    swimlane: Array<DailyWorkloadItem>
    stats: Array<StatValue>
}

export type User = {
    id: String
    login: String
    avatarUrl: String
    fullName: String
    email: String
    banned: Boolean
    team: String
}

export type DailyWorkloadItem = {
    date: String
    workload: Number
    issues: Array<IssueInfo>
}

export type IssueInfo = {
    id: String
    idReadable: String
    summary: String
    startDate: String
    verificationDate: String
    endDate: String
}

export type StatValue = {
    key: String
    value: Number
    label: String
    precision: Number
    unit: String
}

export type SwimlaneRow = {
    label: String
    class?: String
    swimlane?: Array<DailyWorkloadItem>
}