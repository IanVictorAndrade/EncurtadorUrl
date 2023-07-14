package tjro.mapper

interface Mapper<T, U> {

    fun map(t: T): U

}
