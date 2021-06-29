package com.xcart.prognosis.domain

import com.fasterxml.jackson.annotation.JsonCreator

enum class IssueState {
    Waiting,
    Assigned,
    Open,
    Scheduled,
    Submitted,
    InProgress,
    QualityAssurance,
    HasDefects,
    QaPassed,
    OnReview,
    Completed,
    Canceled;

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromString(code: String): IssueState {
            return when (code) {
                "Open" -> Submitted
                "Assigned" -> Submitted
                "Waiting" -> Waiting
                "Submitted" -> Submitted
                "Scheduled" -> Scheduled
                "In progress" -> InProgress
                "Quality Assurance" -> QualityAssurance
                "Has Defects" -> HasDefects
                "QA Passed" -> QaPassed
                "On Review" -> OnReview
                "Completed" -> Completed
                "Canceled" -> Canceled
                else -> Submitted
            }
        }
    }
}
