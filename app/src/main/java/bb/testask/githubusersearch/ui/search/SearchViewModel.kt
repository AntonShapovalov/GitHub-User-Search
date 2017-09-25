package bb.testask.githubusersearch.ui.search

import bb.testask.githubusersearch.datamodel.UserLocalModel
import bb.testask.githubusersearch.datamodel.UserRemoteModel
import javax.inject.Inject

/**
 * Get users from remote API, save in local DB and notify [SearchFragment]
 */
class SearchViewModel @Inject constructor() {

    @Inject lateinit var userLocalModel: UserLocalModel
    @Inject lateinit var userRemoteModel: UserRemoteModel


}