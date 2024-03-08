package biz.belcorp.salesforce.core.data.executor


import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.*


class JobExecutor : ThreadExecutor {

    private val workQueue: BlockingQueue<Runnable>

    private val threadPoolExecutor: ThreadPoolExecutor

    private val threadFactory: ThreadFactory

    init {
        this.workQueue = LinkedBlockingQueue()
        this.threadFactory = JobThreadFactory()
        this.threadPoolExecutor = ThreadPoolExecutor(
            INITIAL_POOL_SIZE, MAX_POOL_SIZE,
            KEEP_ALIVE_TIME.toLong(), KEEP_ALIVE_TIME_UNIT, this.workQueue, this.threadFactory
        )
    }

    override fun execute(runnable: Runnable?) {
        if (runnable == null) {
            throw IllegalArgumentException("Runnable to execute cannot be null")
        }
        this.threadPoolExecutor.execute(runnable)
    }

    private class JobThreadFactory : ThreadFactory {
        private var counter = 0

        override fun newThread(runnable: Runnable): Thread {
            return Thread(runnable, THREAD_NAME + counter++)
        }

        companion object {
            private const val THREAD_NAME = "android_"
        }
    }

    override val scheduler: Scheduler
        get() = Schedulers.from(this)

    companion object {

        private const val INITIAL_POOL_SIZE = 3
        private const val MAX_POOL_SIZE = 5

        // Sets the amount of time an idle thread waits before terminating
        private const val KEEP_ALIVE_TIME = 10

        // Sets the Time Unit to seconds
        private val KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS
    }
}
