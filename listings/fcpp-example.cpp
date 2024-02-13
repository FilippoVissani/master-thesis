template <class node_t>
double abf(node_t& node, trace_t call_point, bool source) {
    trace_call trace_caller(node.stack_trace, call_point);
    return nbr(node, 0, INF, [&] (field<double> d) {
        double v = source ? 0.0 : INF;
        return min_hood(node, 1, d + node.nbr_dist(), v);
    });
}
