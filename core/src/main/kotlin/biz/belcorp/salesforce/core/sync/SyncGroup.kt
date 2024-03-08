package biz.belcorp.salesforce.core.sync

/**
 * SyncGroup: Grupos/Temáticas de Sincronización.
 */
enum class SyncGroup {

    /** NONE: No invocada */
    NONE,

    /** LOGIN: Inicia en el LOGIN */
    LOGIN,

    /** HOME_ON_LOGIN: Inicia despues del LOGIN, en HOME.  */
    HOME_ON_LOGIN,

    /** HOME: Inicia al ver HOME.  */
    HOME,

    /** HOME_FORCED_ON_DEMAND: Inicia a petición del usuario en el HOME.  */
    HOME_FORCED_ON_DEMAND,

    /** CONSULTANTS_LEGACY: Sincroniza Consultoras Legacy.  */
    CONSULTANTS_LEGACY,

    /** DEVELOPMENT_PATH_DASHBOARD: Sincroniza Dashboard de RDD.  */
    DEVELOPMENT_PATH_DASHBOARD,

    /** DEVELOPMENT_MATERIAL: Sincroniza Módulo de Materiales de Desarrollo.  */
    DEVELOPMENT_MATERIAL,

    /** INSPIRA: Sincroniza Módulo de Inspira.  */
    INSPIRA,

    /** POSTULANTS: Sincroniza Módulo de Postulantes.  */
    POSTULANTS,

    /** POSTULANTS_APPLICATIONS: Sincroniza sólo Postulantes de Unete.  */
    POSTULANTS_APPLICATIONS,

    /** TERMS_CONDITIONS: Sincroniza Módulo de Términos y Condiciones.  */
    TERMS_CONDITIONS,

    /** VIRTUAL_METHODOLOGY: Sincroniza Módulo de Metodología Virtual.  */
    VIRTUAL_METHODOLOGY,

    /** CONSULTANTS_PENDING_DEBT: Sincronizacion por Eventos de la Deuda Pendiente Consultora.  */
    CONSULTANTS_PENDING_DEBT,

    /** KPI_EDA: Sincronizacion por Eventos KPIS.  */
    KPI_EDA,

    /** COLLECTION_EDA: Sincronizacion por Eventos Cobranza.  */
    COLLECTION_EDA,

    /** CONSULTANTS_EDA: Sincronizacion por Eventos Consultoras.  */
    CONSULTANTS_EDA,

    /** CAMPAIGNS_EDA: Sincronizacion por Eventos de Campañas.  */
    CAMPAIGNS_EDA,

    /** SEARCH_FILTERS_EDA: Sincronizacion por Eventos de Filtros de Busqueda (Pedidos Web y Busqueda Consultoras).  */
    SEARCH_FILTERS_EDA

}
