package biz.belcorp.salesforce.modules.calculator.util

fun decreaseFiveCampaigns(currentCampaign: Int, isPuertoRico: Boolean) : String{
    val PUERTO_RICO_MAX_CAMPAIGNS_NUMBER = 13
    val OTHERS_MAX_CAMPAIGNS_NUMBER = 18
    val maxConstantValue = if (isPuertoRico) PUERTO_RICO_MAX_CAMPAIGNS_NUMBER else OTHERS_MAX_CAMPAIGNS_NUMBER

    return decreaseFunction(maxConstantValue, currentCampaign)
}

private fun decreaseFunction(maxConstant: Int , currentCampaign: Int): String {
    val campaignSubstraction = currentCampaign - 5
    return if(campaignSubstraction <= 0){
        (campaignSubstraction + maxConstant).toString()
    }else{
        campaignSubstraction.toString()
    }
}
