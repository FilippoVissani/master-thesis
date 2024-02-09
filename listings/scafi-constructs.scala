trait Constructs {
    def nbr[A](expr: => A): A
    def rep[A](init: => A)(fun: (A) => A): A
    def foldhood[A](init: => A)(aggr: (A, A) => A)(expr: => A): A
    def aggregate[A](f: => A): A
    def align[K,V](key: K)(comp: K => V): V
    def share[A](init: => A)(fun: (A, () => A) => A): A
    def mid(): ID
    def sense[A](name: CNAME): A
    def nbrvar[A](name: CNAME): A
}